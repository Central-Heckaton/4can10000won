import React from "react";
import styles from "./comment.module.css";

const Comment = () => {
  return (
    <>
      <div className={styles.commentBox}>
        <div className={styles.arrow}>
          <img src="/img/arrow.png" alt="arrow" />
        </div>
        <div className={styles.comment}>
          <div className={styles.profile}>
            <img
              src="https://res.cloudinary.com/ratebeer/image/upload/d_beer_img_default.png,f_auto/beer_730"
              alt="comment profile"
              className={styles.profilePhoto}
            ></img>
          </div>
          <div className={styles.commentText}>
            <div className={styles.username}>닉네임</div>
            안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요
          </div>
        </div>
      </div>
    </>
  );
};

export default Comment;
