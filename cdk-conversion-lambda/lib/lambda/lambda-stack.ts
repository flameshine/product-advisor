import { Stack, StackProps, Duration, CfnOutput } from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { Function, Code, Runtime } from 'aws-cdk-lib/aws-lambda';

export class LambdaStack extends Stack {
    constructor(scope: Construct, id: string, props?: StackProps) {
        super(scope, id, props);

        const conversionLambdaFunction = new Function(this, 'ConversionLambdaHandler', {
            code: Code.fromAsset('lib/lambda/handler'),
            functionName: 'ConversionLambda',
            handler: 'handler',
            memorySize: 512,
            runtime: Runtime.NODEJS_18_X,
            timeout: Duration.minutes(1)
        });

        new CfnOutput(this, 'ConversionLambdaHandlerOutput', {
            value: conversionLambdaFunction.functionArn,
            exportName: 'conversion-lambda-handler',
        });

        new Function(this, 'ConversionLambdaAuthorizer', {
            code: Code.fromAsset('lib/lambda/authorizer'),
            functionName: 'ConversionLambdaAuthorizer',
            handler: 'handler',
            memorySize: 128,
            runtime: Runtime.NODEJS_18_X,
            timeout: Duration.seconds(30)
        });

        new CfnOutput(this, 'ConversionLambdaAuthorizerOutput', {
            value: conversionLambdaFunction.functionArn,
            exportName: 'conversion-lambda-authorizer',
        });
    }
}