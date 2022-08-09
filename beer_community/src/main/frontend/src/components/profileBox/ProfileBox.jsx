import React, { useRef, useState, useEffect } from "react";
import styles from "./profileBox.module.css";
import axios from 'axios';

const ProfileBox = () => {
  const [image, setImage] = useState(
    "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
  );
  const [username, setUsername] = useState("");
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
  const handleEditSubmit = async (e) => {
    e.preventDefault();
    const request_data = {'username': username, 'imageUrl': image};
    try{
        let response = await axios({
            method: 'post',
            url: '/api/update-user-info',
            headers: {'Content-Type': "application/json"},
            data: JSON.stringify(request_data)
        });
        if(response.status >= 200 && response.status<300){
            alert("정보 수정이 완료되었습니다.");
            return;
        }
        else{
            alert("정보 수정에 실패했습니다.");
            return;
        }
    } catch (err) {
        console.log("handleEditSubmit/err: ", err);
    };
    return;
  }
  useEffect(() => {
    axios.get('/api/user')
    .then((response) => {
        console.log('response.data: ', response.data);
        setUsername(response.data['username'])
    })
  }, []);
  return (
    <>
      <form onSubmit={handleEditSubmit}>
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
          <div className={styles.username}>
            <label>
              <input
                type="text"
                className={styles.usernameInput}
                value = {username}
                onChange = {(e) => setUsername(e.target.value) }
                maxLength="8"
              />

              <img
                className={styles.pencil}
                src="./img/greenPencil.png"
                alt="edit nickname"
              />
            </label>
          </div>
            <button className={styles.submitButton} type="submit">
              수정하기
            </button>
        </div>
      </form>
    </>
  );
};

export default ProfileBox;
