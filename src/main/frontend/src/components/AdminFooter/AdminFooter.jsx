import React from 'react';
import styles from './AdminFooter.module.css';
import { axios } from 'axios';

const AdminFooter = (props) => {
        const handleDeleteClick = async() => {
                try {
                        let response = await axios.get("/api/reported-comments/delete/{reportedCommentId}");
                        console.log(response);
                }
                catch (error) { 
                        console.log(error);
                }
        }

        return (
                <div className={styles.container}>
                        <button className={styles.delete} onClick={handleDeleteClick}>삭제</button>
                </div>
        );
}

export default AdminFooter;