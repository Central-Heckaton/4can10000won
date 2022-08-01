import React, { useRef, useState } from "react";
import styles from "./profileBox.module.css";
const ProfileBox = () => {
  const [image, setImage] = useState(
    "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
  );
  const [nickname, setNickname] = useState("닉네임");
  const fileInput = useRef(null);
  const onChangeImg = (e) => {
    if (e.target.files[0]) {
      // const img=new FormData();
      // img.append("file",e.target.files[0]);
      // axios.post("...",img).then((res)=>{
      // setImage(res.data);
      // }).catch((err)=>{
      // console.error(err)
      // });

      setImage(e.target.files[0]);
    } else {
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      if (reader.readyState === 2) {
        setImage(reader.result);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };
  const onSubmit = ()=>{
  
  }
  return (
    <>
      <form onSubmit={onSubmit}>
        <div className={styles.mainBox}>
          <div
            className={styles.circle}
            onClick={() => {
              fileInput.current.click();
            }}
          >
            <div className={styles.tinyCircle}>
              <img src="./img/pencil.png" alt="edit img" />
            </div>
            <img className={styles.profileImg} src={image} alt="img" />
            <input
              type="file"
              className={styles.input}
              accept="image/*"
              name="profileImg"
              onChange={onChangeImg}
              ref={fileInput}
            />
          </div>
          <div className={styles.nickname}>
            <label>
              <input
                type="text"
                className={styles.nicknameInput}
                placeholder={nickname}
                maxLength="8"
              />

              <img
                className={styles.pencil}
                src="./img/greenPencil.png"
                alt="edit nickname"
              />
            </label>
          </div>

          <div className={styles.passwordBox}>
            <label className={styles.label}>비밀번호</label>
            <input
              type="password"
              className={styles.passwordInput}
              name="password"
            />
            <label className={styles.label}>비밀번호 확인</label>
            <input
              type="password"
              className={styles.passwordInput}
              name="passwordCheck"
            />
            <button className={styles.submitButton} type="submit">
              수정하기
            </button>
          </div>
        </div>
      </form>
    </>
  );
};

export default ProfileBox;
