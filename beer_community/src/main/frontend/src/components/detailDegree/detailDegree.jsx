import React from 'react';
import styles from './detailDegree.module.css'

const DetailDegree = (props) => {
    return (
        <div className={styles.container}>
            <p className={styles.title}>Alchol degree</p>
            <p className={styles.degree}>4.5%</p>
        </div>
    );
};

export default DetailDegree;