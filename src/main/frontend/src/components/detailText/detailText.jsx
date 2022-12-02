import React from 'react';
import styles from './detailText.module.css';

const DetailText = (props) => {
    return (
        <div className={styles.container}>
            <p>
                {props.information}
            </p>
        </div>
    );
};

export default DetailText;