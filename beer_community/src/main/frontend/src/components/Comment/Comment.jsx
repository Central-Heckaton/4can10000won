// comment.jsx
import React from "react";
import { Link } from "react-router-dom";
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
              src={
                props.userImageUrl
                  ? props.userImageUrl
                  : "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
              }
              className={styles.profilePhoto}
              alt="profilePhoto"
            ></img>
          </div>
          <div className={styles.commentText}>
            <div className={styles.commentMenu}>
              <div className={styles.username}>{props.username}</div>
              <div>
            
               
                <img
                  src="/img/trash.png"
                  alt="delete"
                  className={styles.delete}
                  onClick={()=>{

                  }}
                />
              </div>
            </div>

            {props.content}
          </div>
        </div>
      </div>
    </>
  );
};

export default Comment;
