import React from 'react';
import styles from './main_buttons.module.css'

const MainButtons = (props) => (
    <div className={styles.container}>
        <button className={styles.button}>
            편의점 맥주 검색 🔍
        </button>
        <button className={styles.button}>
            맥주 랜덤 추첨 🎰
        </button>
        <button className={styles.button}>
            캔맥주 더 맛있게 먹는 법 🍺
        </button>
        <button className={styles.button}>
            맥주 종류를 잘 모른다면? 🙌
        </button>  
    </div> 
);

export default MainButtons;