// ReviewBox.jsx
import React from 'react';
import EachReview from '../EachReview/EachReview';
import styles from './reviewBox.module.css';
import { useState, useEffect } from 'react';
import axios from 'axios';

const ReviewBox = (props) => {
    const [reviews, setReviews] = useState([]);

    useEffect(() => {
        const getReviews = async() => {
            let response = await axios.get(`/api/comments/${props.id}`);
            setReviews(response.data.data);
        }
        getReviews();
    }, []);


    return (
        <div className={styles.main}>
            {reviews.map((i)=> (
                <EachReview 
                    key={i.id}
                    parentsId={i.id}
                    username={i.username}
                    userImageUrl={i.userImageUrl ? i.userImageUrl : "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"}
                    content={i.content}
                    point={i.point}
                    createdDate={i.createdDate}
                    id={props.id}
                />
            ))}
        </div>
    );
};

export default ReviewBox;