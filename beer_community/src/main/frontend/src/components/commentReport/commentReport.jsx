import React from 'react';
import styles from "./commentReport.module.css";

const commentReport = (props) => {

  const closeModal = () => {
    props.closeModal();
  }

  return (
    <div className="Modal" onClick={closeModal}>
      <div className="modalBody" onClick={(e) => e.stopPropagation()}>
        <button id="modalCloseButton" onclick={closeModal}>
          ✖
        </button>
        {props.children}
      </div>
    </div> 
  );
}

export default commentReport;