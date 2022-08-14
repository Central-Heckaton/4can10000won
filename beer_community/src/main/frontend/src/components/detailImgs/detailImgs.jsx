import React from 'react';
import styles from './detailImgs.module.css'

const DetailImgs = (props) => {
    return (
        <div className={styles.container}>
            <h1 className={styles.name}>{props.beerName}</h1>
            <img className={styles.img} src={props.imageUrl} alt="beerImg" />
            <div className={styles.circle}></div>
        </div>
    );
};

export default DetailImgs;