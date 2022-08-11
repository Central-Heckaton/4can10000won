import React from 'react';
import styles from './detailFooter.module.css';
import { useState } from 'react';

const DetailFooter = () => {
    const [like, setLike] = React.useState(null);
    const [state, setState] = useState('false');

    const onLikeClick = (e) => {
        e.preventDefault();
        setLike(e.target.id);
        setState(e.target.id); 
        console.log(e.target.id);
    };

    React.useEffect(
        (e) => {
            if (like === 'false') {
                let cur = document.getElementById(like);
                cur.style.display = "none";
                cur = document.getElementById('true');
                cur.style.display = "block";
            }

            if (like === 'true') {
                let cur = document.getElementById(like);
                cur.style.display = "none";
                cur = document.getElementById('false');
                cur.style.display = "block";
            }

            setLike(like);
        }, [like]
    );

    return (
        <div className={styles.container}>
            <div className={styles.goReview}>
                <p className={styles.title}>리뷰</p>
                <p className={styles.reviewNum}>(800)</p>
            </div>
            <div className={styles.goRate}>
                <button className={styles.button}>나도 평가하기</button>
            </div>
            <div className={styles.like}>
                <button className={styles.heart}>
                    <img
                        id='false'
                        className={styles.heartReg} 
                        src="/img/heart-regular.png" 
                        alt=""
                        onClick={onLikeClick} />
                    <img 
                        id='true'
                        className={styles.heartSol} 
                        src="/img/heart-solid.png" 
                        alt=""
                        onClick={onLikeClick}
                        />
                </button>
            </div>
        </div>
    );
};

export default DetailFooter;