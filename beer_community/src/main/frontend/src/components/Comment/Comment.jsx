// comment.jsx
import React from "react";
import styles from "./comment.module.css";

const Comment = (props) => {
  console.log(props.userImageUrl);
  return (
    <>
      <div className={styles.commentBox}>
        <div className={styles.arrow}>
          <img src="/img/arrow.png" alt="arrow" />
        </div>
        <div className={styles.comment}>
          <div className={styles.profile}>
            <img
              src={props.userImageUrl ? props.userImageUrl : "/img/default.png"}
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