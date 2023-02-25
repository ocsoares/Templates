import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { UserModule } from './modules/user - EXAMPLE/user.module';
import { AuthModule } from './modules/auth/auth.module';
import { APP_GUARD } from '@nestjs/core';
import { JwtAuthGuard } from './modules/auth/guards/jwt-auth.guard';
import { LoginValidationBodyModule } from './modules/login-validation-body/login-validation-body.module';
// import { MongooseModule } from '@nestjs/mongoose';

@Module({
    imports: [
        ConfigModule.forRoot({
            isGlobal: true,
            envFilePath: '.env',
        }),
        // Conexão do Banco de Dados aqui porque no Módulo do Mongoose iria fazer com que os Testes
        // Conectassem no Banco de Dados REAL ao invés do In Memory !!!
        // OBS: Está comentado apenas de exemplo se NÃO for usar o Banco de Dados com Mongoose...
        // MongooseModule.forRoot(process.env.ATLAS_URL_CONNECTION),
        UserModule,
        AuthModule,
        LoginValidationBodyModule,
    ],
    providers: [
        {
            // Ativa GLOBALMENTE o JwtAuthGuard para a Aplicação, e para DESATIVAR para uma Rota,
            // usar o Decorator Personalizado @IsPublic() no Controller !!!
            provide: APP_GUARD,
            useClass: JwtAuthGuard,
        },
    ],
})
export class AppModule {}
