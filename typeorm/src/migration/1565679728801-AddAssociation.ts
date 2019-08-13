import {MigrationInterface, QueryRunner} from "typeorm";

export class AddAssociation1565679728801 implements MigrationInterface {

    public async up(queryRunner: QueryRunner): Promise<any> {
        await queryRunner.query(`ALTER TABLE "appointments" ADD "employee_id" int`);
        await queryRunner.query(`ALTER TABLE "appointments" ADD CONSTRAINT "FK_f4e3a19c74dac65a223368fa9a0" FOREIGN KEY ("employee_id") REFERENCES "employees"("id") ON DELETE NO ACTION ON UPDATE NO ACTION`);
    }

    public async down(queryRunner: QueryRunner): Promise<any> {
        await queryRunner.query(`ALTER TABLE "appointments" DROP CONSTRAINT "FK_f4e3a19c74dac65a223368fa9a0"`);
        await queryRunner.query(`ALTER TABLE "appointments" DROP COLUMN "employee_id"`);
    }

}
