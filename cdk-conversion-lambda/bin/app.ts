import { App } from 'aws-cdk-lib';
import { LambdaStack } from '../lib/lambda-stack';
import { ApiGatewayStack } from "../lib/api-gateway-stack";

const app = new App();

const lambdaStack = new LambdaStack(app, 'LambdaStack');
const apiGatewayStack = new ApiGatewayStack(app, 'ApiGatewayStack');

apiGatewayStack.addDependency(lambdaStack);