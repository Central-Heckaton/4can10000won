// EachReview.jsx
import React from "react";
import { useState } from "react";
import Comment from "../Comment/Comment";
import ViewStar from "../ViewStar/ViewStar";
import styles from "./eachReview.module.css";
import axios  from 'axios';

const EachReview = (props) => {
  const [showComment, setShowComment] = useState(false);
  const [recomments, setReComments] = useState([]);

  const [beerId, setBeerId] = useState(props.id);
  const [parentId, setParentId] = useState(0);
  const [content, setContent] = useState("");

  const handleDropdownClick = (e) => {
    setShowComment(!showComment);
    const getRecomments = async() => {
      let response = await axios.get(`/api/recomments/${props.id}`);
      setReComments(response.data.data);
    }
    getRecomments();
    console.log('wefjoiw: ', recomments);
  };

  const handleReClick = async(e) => {
    const request_data = { beerId: beerId, parentId: parentId, content: content };
    let response = await axios({
      method: "post",
      url: "/api/comments/write-recomment",
      headers: { "Content-Type": "application/json" },
      data: JSON.stringify(request_data),
    });
    console.log(response);
    console.log('content', content);
  }

  return (
    <>
      <div className={showComment ? styles.mainDropdown : styles.main}>
        <div className={styles.profile}>
          <img
            className={styles.profilePhoto}
            src={props.userImageUrl}
            alt=""
          />
          <div className={styles.username}>{props.username}</div>
        </div>
        <div className={styles.reviewBox}>
          <ViewStar point={props.point}/>
          <div className={styles.writeTime}>작성날짜   {props.createdDate} </div>
          <div className={styles.review}>
            {props.content}
          </div>
        </div>
        <div className={styles.dropdownBox}>
          <img
            src="/img/dropbutton.png"
            alt="dropdown"
            className={showComment ? styles.dropdownSpin : styles.dropdown}
            onClick={handleDropdownClick}
          ></img>
        </div>
      </div>

      {showComment ? (
        <>
          <div className={styles.dropdownMain}>
            {recomments.map((i) => (
              <Comment 
                key={i.id}
                id={i.id}
                username={i.username}
                content={i.content}
                createdDate={i.createdDate}
              />
            ))}

            <div className={styles.inputBox}>
              <input 
                placeholder="댓글을 입력하세요." 
                className={styles.input} 
                type="text" 
                value={content} 
                onChange={(e) => setContent(e.target.value)} 
              />
              <button 
                className={styles.button} 
                type="submit"
                onClick={handleReClick}  
              >
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