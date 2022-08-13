import React from "react";
import { useState } from "react";
import Comment from "../Comment/Comment";
import ViewStar from "../ViewStar/ViewStar";
import styles from "./eachReview.module.css";

const EachReview = (props) => {
  const [showComment, setShowComment] = useState(false);
  return (
    <>
      <div className={showComment ? styles.mainDropdown : styles.main}>
        <div className={styles.profile}>
          <img
            className={styles.profilePhoto}
            src="https://res.cloudinary.com/ratebeer/image/upload/d_beer_img_default.png,f_auto/beer_730"
            alt=""
          />
          <div className={styles.username}>닉네임</div>
        </div>
        <div className={styles.reviewBox}>
          <ViewStar />
          <div className={styles.writeTime}>작성날짜{props.writeTime} </div>
          <div className={styles.review}>
            안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요
          </div>
        </div>
        <div className={styles.dropdownBox}>
          <img
            src="/img/dropbutton.png"
            alt="dropdown"
            className={showComment ? styles.dropdownSpin : styles.dropdown}
            onClick={() => {
              setShowComment(!showComment);
            }}
          ></img>
        </div>
      </div>

      {showComment ? (
        <>
          <div className={styles.dropdownMain}>
            {/* map 함수 써야함 Comment 컴포넌트 */}
            <Comment />
            <Comment />

            <div className={styles.inputBox}>
            {/* 댓글입력 form 필요? */}
              <input placeholder="댓글을 입력하세요." className={styles.input} type="text" />
              <button className={styles.button} type="submit">
                <img src="/img/vector.png" alt="button" />
              </button>
            </div>
          </div>
        </>
      ) : null}
    </>
  );
};

export default EachReview;
