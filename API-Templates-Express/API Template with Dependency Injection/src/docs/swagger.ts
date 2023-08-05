import { OpenAPIV3 } from 'openapi-types';
export const swaggerJSON: OpenAPIV3.Document = {
    openapi: '3.0.0', // Update if necessary

    info: {
        version: '1.0.0', // Update if necessary
        title: 'anytitle',
        description: 'anydescription',
        // termsOfService: 'Rota dos termos...',
        contact: {
            email: 'anyemail'
        }
    },

    paths: {
        "/route": { // any_route
            post: { // any_http_method
                summary: 'Any sumary',
                description: 'anydescription',
                tags: ['Anytag'],
                requestBody: {
                    description: 'anydescription',
                    content: {
                        "application/json": {
                            schema: {
                                $ref: "#/components/schemas/AnyNameSchema"
                            },
                            examples: {
                                register: {
                                    value: {
                                        any_prop: 'any',
                                    }
                                }
                            }
                        }
                    }
                },
                responses: {
                    400: { // any_http_error
                        description: 'anydescription'
                    },
                    201: { // any_http_error
                        description: 'anydescription',
                        content: {
                            "application/json": {
                                schema: {
                                    $ref: '#/components/schemas/AnyNameSchema'
                                }
                            }
                        }
                    }
                }
            },
        },
        "/other-route": { // any_route
            post: { // any_http_method

                // same as above

                responses: {
                    // same as above
                }
            }
        },

        // more routes...
    },
    components: {
        schemas: {
            any_schema: {
                type: 'object', // change if necessary
                properties: {
                    any_prop: {
                        type: 'string' // change if necessary
                    },
                    another_prop: {
                        type: 'string' // change if necessary
                    },
                }
            },
            another_schema: {
                type: 'object', // change if necessary
                // same as above
            }
        },
        securitySchemes: {
            bearerAuth: {
                description: 'Autenticação nas rotas protegidas utilizando JWT',
                type: 'http', // change if necessary
                scheme: 'bearer',
                bearerFormat: 'JWT'
            }
        }
    }
};