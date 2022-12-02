import React from 'react';
import styles from './reviewButton.module.css';
const ReviewButton = () => {
    return (
        <div className={styles.main}>
            <div className={styles.button}>리뷰</div>
        </div>
    );
};

export default ReviewButton;