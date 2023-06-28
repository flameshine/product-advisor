import { CfnOutput, Duration, Stack, StackProps } from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { Code, Function, Runtime } from 'aws-cdk-lib/aws-lambda';
import { RetentionDays } from 'aws-cdk-lib/aws-logs';

export class LambdaStack extends Stack {
    constructor(scope: Construct, id: string, props?: StackProps) {
        super(scope, id, props);

        const conversionLambdaFunction = new Function(this, 'ConversionLambdaHandler', {
            code: Code.fromAsset('lib/lambda/handler'),
            functionName: 'ConversionLambda',
            handler: 'conversion.handler',
            memorySize: 512,
            runtime: Runtime.NODEJS_18_X,
            timeout: Duration.minutes(1),
            logRetention: RetentionDays.ONE_DAY,
        });

        new CfnOutput(this, 'ConversionLambdaHandlerOutput', {
            value: conversionLambdaFunction.functionArn,
            exportName: 'conversion-lambda-handler-arn',
        });
    }
}