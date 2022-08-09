import React from 'react';
import styles from './detailText.module.css';

const DetailText = (props) => {
    return (
        <div className={styles.container}>
            <p>
                2020년 대한제분과의 콜라보로 출시되어 큰 화제를 낳았다.
            </p>
        </div>
    );
};

export default DetailText;