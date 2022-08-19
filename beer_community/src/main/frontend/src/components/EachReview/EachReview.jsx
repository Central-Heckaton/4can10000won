// EachReview.jsx
import React from "react";
import { useState } from "react";
import Comment from "../Comment/Comment";
import ViewStar from "../ViewStar/ViewStar";
import styles from "./eachReview.module.css";
import axios from "axios";
import { Link } from "react-router-dom";

const EachReview = (props) => {
  const [showComment, setShowComment] = useState(false);
  const [recomments, setReComments] = useState([]);
  const [beerId, setBeerId] = useState(props.beerId);
  const [parentId, setParentId] = useState(props.parentId);
  const [content, setContent] = useState("");

  const resetInput = () => {
    document.getElementById("input").value = "";
    setContent("");
  };
  const getRecomments = async () => {
    let response = await axios.get(`/api/recomments/${props.parentId}`);
    setReComments(response.data.data);
  };
  const handleDropdownClick = (e) => {
    setShowComment(!showComment);

    getRecomments();
  };

  const handleReClick = async (e) => {
    const request_data = {
      beerId: beerId,
      parentId: parentId,
      content: content,
    };
    console.log(request_data);
    let response = await axios({
      method: "post",
      url: "/api/comments/write-recomment",
      headers: { "Content-Type": "application/json" },
      data: JSON.stringify(request_data),
    });
    console.log(response);
    getRecomments();
    resetInput();
  };

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
          <div className={styles.reviewBoxMenu}>
            <div className={styles.viewStar}>
              <ViewStar point={props.point} />
            </div>
            {props.edit && (
              <div className={styles.editDelete}>
                <Link
                  to={"/editrate"}
                  state={{ commentId: parentId, id: beerId }}
                >
                  <img
                    src="/img/pencilEdit.png"
                    alt="edit"
                    className={styles.edit}
                  />
                </Link>
                <img
                  src="/img/trash.png"
                  alt="trash"
                  onClick={() => {
                    const commentDelete = async () => {
                      console.log("parentId : ", parentId);
                      await axios
                        .get(`/api/comments/delete-comment/${parentId}`)
                        .then((response) => {
                          console.log("delete/response: ", response);
                          getRecomments();
                        })
                        .catch((error) => {
                          console.log("err: ", error);
                        })
                        .then(() => {});
                    };
                    commentDelete();
                    const getReviews = async () => {
                      let response = await axios.get(
                        `/api/comments/${props.beerId}`
                      );
                      props.setReviews(response.data.data);
                    };
                    getReviews();
                  }}
                />
              </div>
            )}
          </div>

          <div className={styles.writeTime}>작성날짜 {props.createdDate} </div>
          <div className={styles.review}>{props.content}</div>
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
                delete={i.writerId === props.userId ? true : false}
                id={i.id}
                username={i.username}
                content={i.content}
                createdDate={i.createdDate}
                getRecomments={getRecomments}
              />
            ))}

            <div className={styles.inputBox}>
              <input
                id="input"
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
