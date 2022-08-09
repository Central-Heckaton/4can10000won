import React from 'react';
import styles from './beerBox.module.css'

const BeerBox = (props) => {
    return (
        <div className={styles.box}>
            <img className={styles.img} src={props.image} alt="" />
            <div className={styles.text}>
                <h1 className={styles.name}>{props.title}</h1>
                <div className={styles.rate}>
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                </div>
                <p className={styles.description}>
                    {props.description}
                </p>
            </div>
        </div>
    );
};
export default BeerBox;