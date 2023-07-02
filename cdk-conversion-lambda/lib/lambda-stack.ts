import { CfnOutput, Duration, Stack, StackProps } from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { Code, Function, Runtime } from 'aws-cdk-lib/aws-lambda';
import { RetentionDays } from 'aws-cdk-lib/aws-logs';
import { ServicePrincipal } from "aws-cdk-lib/aws-iam";

export class LambdaStack extends Stack {
    constructor(scope: Construct, id: string, props?: StackProps) {
        super(scope, id, props);

        const conversionLambdaHandler = new Function(this, 'ConversionLambdaHandler', {
            code: Code.fromAsset('src'),
            functionName: 'ConversionLambda',
            handler: 'conversion.handler',
            memorySize: 512,
            runtime: Runtime.NODEJS_18_X,
            timeout: Duration.minutes(1),
            logRetention: RetentionDays.ONE_DAY,
        });

        conversionLambdaHandler.grantInvoke(
            new ServicePrincipal('apigateway.amazonaws.com')
        );

        new CfnOutput(this, 'ConversionLambdaHandlerOutput', {
            value: conversionLambdaHandler.functionArn,
            exportName: 'conversion-lambda-handler-arn',
        });
    }
}