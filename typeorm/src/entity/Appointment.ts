import { Column, Entity, PrimaryGeneratedColumn, CreateDateColumn, UpdateDateColumn, ManyToOne, JoinColumn } from "typeorm";
import { Employee } from "./Employee";

@Entity("appointments")
export class Appointment {

    @PrimaryGeneratedColumn()
    id: number;

    @Column("datetime")
    timeslot;

    @ManyToOne(type => Employee)
    @JoinColumn({name: "employee_id"})
    employee;

    @CreateDateColumn({name: "created_at"})
    createdAt;

    @UpdateDateColumn({name: "updated_at"})
    updatedAt;

}
