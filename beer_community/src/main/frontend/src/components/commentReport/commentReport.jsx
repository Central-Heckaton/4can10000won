import React, { useState } from 'react';
import axios from "axios";
import styles from "./commentReport.module.css";
import styled from "styled-components";

const REASON_DATA = [
  { id: null, value: '신고 사유를 선택해주세요.' },
  { id: '0001', value: '스팸홍보/도배' },
  { id: '0002', value: '음란물' },
  { id: '0003', value: '불법 정보' },
  { id: '0004', value: '욕설' },
  { id: '0005', value: '개인정보 노출' },
  { id: '0006', value: '불쾌한 표현' },
];
const CommentReport = ({ onClose, content }) => {

  const handleClose = () => {
    onClose?.();
  };
  /*
  console.log(props)
  console.log("a", props.onClose)
  console.log(props.content)
  console.log(props.open)*/

  return (
    

    <Overlay>
      <ModalWrap>
        <CloseButton onClick={handleClose}>✖</CloseButton>
        
        <Contents>
          <h1>댓글 신고</h1>      

          <comment>신고할 댓글</comment>
          <div>{content}</div>

          <reportReason>
          <h2>신고 사유 선택</h2>
          <fieldset>
          <label><input type="radio" value="reason" name={"스팸홍보/도배"} />스팸홍보/도배<br/></label>
          <label><input type="radio" value="reason" name={"음란물"} />음란물<br/></label>
          <label><input type="radio" value="reason" name={"불법 정보"} />불법 정보<br/></label>
          <label><input type="radio" value="reason" name={"욕설"} />욕설<br/></label>
          <label><input type="radio" value="reason" name={"개인정보 노출"} />개인정보 노출<br/></label>
          <label><input type="radio" value="reason" name={"불쾌한 표현"} />불쾌한 표현<br/></label>
          </fieldset>
          </reportReason>

          <Button onClick={handleClose}>신고</Button>
          <Button onClick={handleClose}>취소</Button>

        </Contents>
      </ModalWrap>
    </Overlay>

  );
}

const Overlay = styled.div`
  position: fixed;
  width: 100%;
  height 100%;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.2);
  z-index: 9999;
`;

const ModalWrap = styled.div`
  width: 400px;
  height: fit-content;
  border-radius: 15px;
  background-color: #fff;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

const CloseButton = styled.div`
  float: right;
  width: 40px;
  height: 40px;
  margin-top: 50px;
  margin-right: 20px;
  cursor: pointer;
  i {
    color: #5d5d5d;
    font-size: 30px;
  }
`;  

const Contents = styled.div`
  margin: 50px 30px;
  h1 {
    font-size: 30px;
    font-weight: 600;
    margin-bottom: 60px;
  }
  white-space: pre-wrap;
`;

const Button = styled.div`
  font-size: 14px;
  padding: 10px 20px;
  border: none;
  background-color: #5d5d5d;
  border-radius: 10px;
  color: white;
  font-style: italic;
  font-weight: 200;
  cursor: pointer;
  margin-top: 20px;

`
const buttonArea = styled.div`
  display: flex;
  justify-content: center;
`
export default CommentReport;