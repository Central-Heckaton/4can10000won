import React from 'react';
import EachReview from '../EachReview/EachReview';
import styles from './reviewBox.module.css';
const ReviewBox = () => {
    return (
        <div className={styles.main}>
        {/* EachReview map 함수로 */}
            <EachReview/>
            <EachReview/>
        </div>
    );
};

export default ReviewBox;