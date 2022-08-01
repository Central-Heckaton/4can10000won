import React from 'react';
import styles from './beerBox.module.css'

const BeerBox = (props) => {
    return (
        <div className={styles.box}>
            <img className={styles.img} src="/img/example.png" alt="" />
            <div className={styles.text}>
                <h1 className={styles.name}>곰표 밀맥주</h1>
                <div className={styles.rate}>
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                </div>
                <p className={styles.description}>
                    2020년 대한제분과의 콜라보로 출시되어 큰 화제를 낳았다.
                </p>
            </div>
        </div>
    );
};
export default BeerBox;