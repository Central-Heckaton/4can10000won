import React from 'react';
import styles from './detailFooter.module.css';

const DetailFooter = () => {
    const [like, setLike] = React.useState(null);

    const onLikeClick = (e) => {
        setLike(e.target.id);
        console.log(e.target.id);
    };

    React.useEffect(
        (e) => {
            if (like == 'heartReg') {
                let cur = document.getElementById(like);
                cur.style.display = "none";
                cur = document.getElementById('heartSol');
                cur.style.display = "block";
            }

            if (like == 'heartSol') {
                let cur = document.getElementById(like);
                cur.style.display = "none";
                cur = document.getElementById('heartReg');
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
                        id='heartReg'
                        className={styles.heartReg} 
                        src="/img/heart-regular.png" 
                        alt=""
                        onClick={onLikeClick} />
                    <img 
                        id='heartSol'
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