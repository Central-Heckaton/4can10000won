import React from 'react';
import styles from './filterBox.module.css';

const FilterBox = (props) => {
    return (
        <div className={styles.container}>
            <button className={styles.box}>라거</button>
            <button className={styles.box}>에일</button>
            <button className={styles.box}>IPA</button>
            <button className={styles.box}>스타우트</button>
        </div>
    );
};

export default FilterBox;