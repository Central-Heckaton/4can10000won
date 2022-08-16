// comment.jsx
import React from "react";
import styles from "./comment.module.css";

const Comment = (props) => {
  return (
    <>
      <div className={styles.commentBox}>
        <div className={styles.arrow}>
          <img src="/img/arrow.png" alt="arrow" />
        </div>
        <div className={styles.comment}>
          <div className={styles.profile}>
            <img
              src={props.userImageUrl}
              alt="comment profile"
              className={styles.profilePhoto}
            ></img>
          </div>
          <div className={styles.commentText}>
            <div className={styles.username}>{props.username}</div>
            {props.content}
          </div>
        </div>
      </div>
    </>
  );
};

export default Comment;