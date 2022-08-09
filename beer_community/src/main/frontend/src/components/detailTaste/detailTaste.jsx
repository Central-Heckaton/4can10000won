import React from 'react';
import styles from './detailTaste.module.css'

const DetailTaste = (props) => {
    return (
        <div className={styles.container}>
            <div className={styles.titleBox}>
                <p className={styles.title}>Taste</p>
                <button className={styles.tag1}>부드러움</button>
                <button className={styles.tag2}>과일향</button>
            </div>
            <div className={styles.totalBox}>
                <p className={styles.total}>총점</p>
                <div className={styles.rate}>
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                </div>
                <p className={styles.rateNum}>(5.0)</p>
            </div>
        </div>
    );
};

export default DetailTaste;