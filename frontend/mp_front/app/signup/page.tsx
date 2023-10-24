"use client";

import styles from '../page.module.css'
import schema from "@/schemes/signupSchema";
import Input from '@/components/Input/Input';
import Form from '@/components/Form/Form';
import { registration } from "@/utils/utils";
import { joiResolver } from '@hookform/resolvers/joi';
import { useForm } from 'react-hook-form';
import { useRouter } from 'next/navigation';

export default function Signup() {
    const router = useRouter()
    const { register, handleSubmit, formState: { errors, isValid, isDirty }} = useForm({      //определяем некоторые параметры формы
        resolver: joiResolver(schema),                                                        //используем joiResolver для валидации полей
        mode: "onChange",                                                                     //onChange для валидации в реальном времени
    });

    function onSubmit(data: { firstName: string; lastName: string; email: string; phoneNumber: string; password: string; }) {  //функция сабмита
        try {
            registration(data.firstName, data.email, data.password);
            router.push('/');
        } catch(err) {
            console.log("возникла ошибка при отправке формы: ", err);
        }
    }

    return (
        <section className={styles.main}>
            <h1>Добро пожаловать</h1>
            <p>Заполните форму для регистрации</p>
            <Form
                buttonLabel="Зарегистрироваться"
                register={register}
                handleSubmit={handleSubmit}
                onSubmit={onSubmit}
                isValid={isValid}
                isDirty={isDirty}
            >
                <Input
                    name="firstName"
                    type="text"
                    label="Имя"
                    placeholder="Введите имя"
                    error={errors.firstName?.message as any}
                    autoFocus
                />
                <Input
                    name="lastName"
                    type="text"
                    label="Фамилия"
                    placeholder="Введите фамилию"
                    error={errors.lastName?.message as any}
                />
                <Input
                    name="email"
                    type="email"
                    label="E-mail"
                    placeholder="Введите электронную почту"
                    error={errors.email?.message as any}
                />
                <Input
                    name="phoneNumber"
                    type="tel"
                    label="Номер телефона"
                    placeholder="Введите номер телефона"
                    error={errors.phoneNumber?.message as any}
                />
                <Input
                    name="password"
                    type="password"
                    label="Пароль"
                    placeholder="Введите пароль"
                    error={errors.password?.message as any}
                />
            </Form>
        </section>
    )
}