import React from 'react';
import styles from './AdminTitle.module.css';

const AdminTitle = (props) => {
        return (
                <div className={styles.container}>
                        <div className={styles.logo}>
                                <img src="/img/logo.png" alt="logo" className={styles.imgLogo} />
                                <h1 className={styles.title}>댓글 신고 내역</h1>
                        </div>
                </div>
        );
}
export default AdminTitle;