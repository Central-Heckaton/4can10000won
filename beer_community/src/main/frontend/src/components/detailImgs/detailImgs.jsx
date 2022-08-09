import React from 'react';
import styles from './detailImgs.module.css'

const DetailImgs = (props) => {
    return (
        <div className={styles.container}>
            <h1 className={styles.name}>곰표 밀맥주</h1>
            <img className={styles.img} src="/img/example.png" alt="" />
            <div className={styles.circle}></div>
        </div>
    );
};

export default DetailImgs;