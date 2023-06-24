import { Stack, StackProps, Fn } from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { Function } from 'aws-cdk-lib/aws-lambda';
import { HttpApi, HttpMethod } from '@aws-cdk/aws-apigatewayv2-alpha';
import { HttpLambdaIntegration } from '@aws-cdk/aws-apigatewayv2-integrations-alpha';
import { HttpLambdaAuthorizer } from '@aws-cdk/aws-apigatewayv2-authorizers-alpha';

export class ApiGatewayStack extends Stack {
    constructor(scope: Construct, id: string, props?: StackProps) {
        super(scope, id, props);

        const api = new HttpApi(this, 'ConversionLambdaHttpApi', {
            apiName: 'conversion-lambda-http-api',
        });

        const conversionLambdaHandlerArn = Fn.importValue('conversion-lambda-handler');
        const conversionLambdaHandler = Function.fromFunctionArn(this, 'ConversionLambdaHandler', conversionLambdaHandlerArn);
        const conversionLambdaIntegration = new HttpLambdaIntegration('ConversionLambdaIntegration', conversionLambdaHandler);
        const conversionLambdaAuthorizerArn = Fn.importValue('conversion-lambda-authorizer');
        const conversionLambdaAuthorizer = Function.fromFunctionArn(this, 'ConversionLambdaAuthorizer', conversionLambdaAuthorizerArn);

        api.addRoutes({
            path: '/convert',
            methods: [ HttpMethod.POST ],
            integration: conversionLambdaIntegration,
            authorizer: new HttpLambdaAuthorizer('ConversionLambdaAuthorizer', conversionLambdaAuthorizer),
        })
    }
}