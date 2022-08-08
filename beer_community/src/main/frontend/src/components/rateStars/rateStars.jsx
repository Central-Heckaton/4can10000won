import React from 'react';
import styles from './rateStars.module.css';

const RateStars = (props) => {
    const clearText = () => {
        document.getElementById('input').value = '';
    };


    return (
        <div className={styles.container}>
            <div className={styles.btnContainer}>
                <button className={styles.button}>리뷰 작성</button>
            </div>
            <div className={styles.starContainer}>
                <p className={styles.total}>총점</p>
                <div className={styles.rate}>
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                    <img className={styles.star} src="/img/star.png" alt="" />
                </div>
            </div>
            <div className={styles.optContainer}>
                <p className={styles.opt}>선택 옵션</p>
                <p className={styles.optName}>곰표 맥주</p>
            </div>
            <div className={styles.footer}>
                <textarea
                    id="input"
                    className={styles.input} 
                    type="text" 
                    placeholder="맥주 평가글을 입력해주세요."/>
                <input 
                    className={styles.register} 
                    type="button" 
                    value="등록"
                    onClick={clearText}/>
            </div>
        </div>
    );
};

export default RateStars;