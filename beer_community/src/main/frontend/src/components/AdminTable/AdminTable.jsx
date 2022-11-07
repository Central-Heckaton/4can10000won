import axios from 'axios';
import React, { useState, useEffect } from 'react';
import styles from './AdminTable.module.css';

const AdminTable = (props) => {
        const [reportData, setReportData] = useState([]);
        const [isCheckAll, setIsCheckAll] = useState(false);  // 전체 다 체크했을 때
        const [isCheckingBox, setIsCheckingBox] = useState(false);  // 하나라도 체크했을 때
        const [checkedArr, setCheckedArr] = useState([]);  // 체크한 항목

        const changeAllCheck = (e) => {
                if (e.target.checked) {
                        setIsCheckAll(true);
                } else {
                        setIsCheckAll(false);
                        setCheckedArr([]);
                }
        }

        const checkingBox = () => {
                setIsCheckAll(false);
                setIsCheckingBox(true);
                setCheckedArr([]);
        }

        const getReportedComments = async() => {
                try {
                        let response = await axios.get("/api/reported-comments?page={page_number}");
                        setReportData(response.data.data);
                        console.log(response);
                } 
                catch (error) {
                        console.log(error);
                }
        }

        useEffect(() => {
                getReportedComments();
        })

        return (
                <div className={styles.container}>
                                <table className={styles.table}>
                                        <thead>
                                                <tr align="center">
                                                        <th>
                                                                <input type="checkbox" name="checkbox" id="all"  />
                                                        </th>
                                                        <th>신고 ID</th>
                                                        <th>작성자 이름</th>
                                                        <th>신고 사유</th>
                                                        <th>내용</th>
                                                        <th>신고 날짜</th>
                                                </tr>
                                        </thead>
                                        <tbody>
                                                {Array.isArray(reportData) ? 
                                                        reportData.map((i) => (
                                                                <tr align="center">
                                                                        <td>
                                                                                <input type="checkbox" name="checkbox" className={styles.checkbox} />
                                                                        </td>
                                                                        <td>{i.id}</td>
                                                                        <td>{i.reporterName}</td>
                                                                        <td>{i.reportType}</td>
                                                                        <td>{i.content}</td>
                                                                        <td>{i.createdData}</td>
                                                                </tr>
                                                        ))
                                                : null }
                                        </tbody>
                                </table>
                        </div>
        );
}

export default AdminTable;