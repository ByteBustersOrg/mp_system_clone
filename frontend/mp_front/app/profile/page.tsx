import globalStyles from '../page.module.css'
import profileStyles from "./page.module.css";
export default function Profile() {
    return (
        <section className={globalStyles.main}>
            <h1 className={profileStyles.profileTitle}>Профиль</h1>
            <ul className={profileStyles.profileDataList}>
                <li className={profileStyles.profileDataItem}>Имя: </li>
                <li className={profileStyles.profileDataItem}>E-mail: </li>
                <li className={profileStyles.profileDataItem}>Номер телефона: </li>
            </ul>
        </section>
    );
}