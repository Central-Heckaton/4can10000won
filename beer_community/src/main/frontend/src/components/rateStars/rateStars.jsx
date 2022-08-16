// rateStars.jsx
import React from 'react';
import styles from './rateStars.module.css';
import { useState, useEffect } from 'react';
import  axios  from 'axios';

const RateStars = (props) => {
    const [starClick, setStarClick] = useState(null);
    const [preClicked, setPreClicked] = useState(starClick);
    
    const [beerId, setBeerId] = useState(props.id);
    const [content, setContent] = useState("");
    const [point, setPoint] = useState(0);

    const resetInput = () => {
        setStarClick(null);
        setPreClicked(starClick);
        setBeerId(props.id);
        setContent("");
        setPoint(0);
        [1, 2, 3, 4, 5].map(i => {document.getElementById(i).src = "/img/star-regular.png"});
    }

    const handleInputClick = async (e) => {
        document.getElementById('input').value = '';
        const request_data = { beerId: beerId, content: content, point: point };
        try {
            let response = await axios({
                method: "post",
                url: "/api/comments/write-comment",
                headers: { "Content-Type": "application/json" },
                data: JSON.stringify(request_data),
            }); 
            if (response.status >= 200 && response.status < 300) {
                alert("댓글 작성이 완료 되었습니다.");
                resetInput();
            }
        } catch(err) {
            // alert(err);
            console.log(err);
            resetInput();
        }
    };

    const goToFetch = (e) => {
        e.preventDefault();
        const nowClicked = e.target.id;
        setStarClick(nowClicked);
        setPoint(nowClicked);

        if(nowClicked !== null) {
            if(nowClicked > preClicked) {
                for (let i = 1; i <= nowClicked; i++) {
                    const star_id = document.getElementById(i);
                    star_id.src = "/img/star.png";
                }
            }
            else if(nowClicked < preClicked){
                for (let i = 1; i <= nowClicked; i++) {
                    const star_id = document.getElementById(i);
                    star_id.src = "/img/star.png";
                }
                for(let j = 5; j > nowClicked; j--) {
                    const star_id = document.getElementById(j);
                    star_id.src = "/img/star-regular.png";
                }
            }
            else {
                for (let i = 1; i <= nowClicked; i++) {
                    const star_id = document.getElementById(i);
                    star_id.src = "/img/star-regular.png";
                }
            }
        }
    }

    useEffect(() => {
        setPreClicked(starClick);
    });

    return (
        <div className={styles.container}>
            <div className={styles.btnContainer}>
                <button className={styles.button}>리뷰 작성</button>
            </div>
            <div className={styles.starContainer}>
                <p className={styles.total}>총점</p>
                <div className={styles.rate}>
                    {[1, 2, 3, 4, 5].map(el => (
                        <img 
                            key={el}
                            id={el}
                            className={styles.star} 
                            src="/img/star-regular.png"
                            alt=""
                            onClick={goToFetch} />
                    ))}
                </div>
            </div>
            <div className={styles.footer}>
                <textarea
                    id="input"
                    className={styles.input} 
                    type="text" 
                    value={content}
                    placeholder="맥주 평가글을 입력해주세요."
                    onChange={(e) => setContent(e.target.value)}
                />
                <input 
                    className={styles.register} 
                    type="button" 
                    value="등록"
                    onClick={handleInputClick}/>
            </div>
        </div>
    );
};

export default RateStars;