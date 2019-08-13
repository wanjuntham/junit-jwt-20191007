import {MigrationInterface, QueryRunner} from "typeorm";

export class InitialTables1565679487743 implements MigrationInterface {

    public async up(queryRunner: QueryRunner): Promise<any> {
        await queryRunner.query(`CREATE TABLE "appointments" ("id" int NOT NULL IDENTITY(1,1), "timeslot" datetime NOT NULL, "created_at" datetime2 NOT NULL CONSTRAINT "DF_387464fb81909344c275230cc08" DEFAULT getdate(), "updated_at" datetime2 NOT NULL CONSTRAINT "DF_c5aff8c60e664ef9724f1cae097" DEFAULT getdate(), CONSTRAINT "PK_4a437a9a27e948726b8bb3e36ad" PRIMARY KEY ("id"))`);
        await queryRunner.query(`CREATE TABLE "employees" ("id" int NOT NULL IDENTITY(1,1), "name" nvarchar(255) NOT NULL, "email" nvarchar(255) NOT NULL, "created_at" datetime2 NOT NULL CONSTRAINT "DF_e1f508d74b2f061e0248c2769cf" DEFAULT getdate(), "updated_at" datetime2 NOT NULL CONSTRAINT "DF_37738e835c865a9caf4635757fe" DEFAULT getdate(), CONSTRAINT "PK_b9535a98350d5b26e7eb0c26af4" PRIMARY KEY ("id"))`);
    }

    public async down(queryRunner: QueryRunner): Promise<any> {
        await queryRunner.query(`DROP TABLE "employees"`);
        await queryRunner.query(`DROP TABLE "appointments"`);
    }

}
