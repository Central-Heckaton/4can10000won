import React, { useRef, useState, useEffect } from "react";
import styles from "./profileBox.module.css";
import axios from 'axios';
import { useNavigate } from "react-router-dom";

const ProfileBox = () => {
  let navigate = useNavigate();
  const [image, setImage] = useState(
    "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
  );
  const [username, setUsername] = useState("");
  const fileInput = useRef(null);
  const [id, setId] = useState(0);

  const onChangeImg = async (e) => {
    if (e.target.files[0]) {
      const img = new FormData();
      img.append("file",e.target.files[0]);
      let response =
          await axios.post(`/api/user/${id}/imageUrl`, {"profilePhoto": img})
              .then((res)=>{
                  console.log("res: ", res);
                  console.log("res.data: ", res.data);
                  setImage(res.data);
              }).catch((err)=>{
                  console.error(err)
              });
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
    } catch (error) {
        console.log("handleEditSubmit/error: ", error);
            console.log('error: ', error)
            console.log('error.response: ', error.response)
            console.log('error.response.data: ', error.response.data)
            console.log('error.response.status: ', error.response.status)
            if (error.response.status == 403){ // Forbidden - 로그인 되지 않은 유저의 접근
                alert("내 프로필을 확인하기 위해선 로그인이 필요합니다.\n로그인 페이지로 이동합니다")
                navigate("/login", {});
            }
    };
    return;
  }
  useEffect(() => {

    try{
        axios.get('/api/user')
        .then((response) => {
            console.log('response.data: ', response.data);
            setId(response.data['id'])
            setUsername(response.data['username'])
            if(response.data['imageUrl'] != null){
                setImage(response.data['imageUrl']);
            }

        })
    } catch(e) {
        console.log('profileBox/useEffect()/error: ', e);
        console.log('profileBox/useEffect()/error.data: ', e.response.data);
        if(e.response.status == 403){
            if(window.confirm(e.response.data.message)){
              navigate('/login', {});
            }
        }
        navigate('/search', {});
    }

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
