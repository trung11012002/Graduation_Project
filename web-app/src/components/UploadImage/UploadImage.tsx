import React, { useEffect, useState } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { message, Upload } from 'antd';
import type { UploadFile, UploadProps } from 'antd/es/upload/interface';

interface IUploadImage {
    setFile?: any
    listFile?: any
    setDeleteThumbnails?: any
    deleteThumbnails?: any
}

const checkObjectInArray = (arr: any, obj: any) => {
    return arr.find((item: any) => item.uid === obj.uid);
};

const filterArray = (array1: any, array2: any) => {
    const deleteElement = array1.filter((element: any) => {
        if (element.uid) {
            return !checkObjectInArray(array2, element)
        }
        return []
    })

    return deleteElement
}

const UploadImage: React.FC<IUploadImage> = ({ setFile, listFile, deleteThumbnails, setDeleteThumbnails }) => {

    const [fileList, setFileList] = useState<UploadFile[]>([]);

    const handleChange: UploadProps['onChange'] = ({ fileList: newFileList }) => {
        const check = filterArray(fileList, newFileList)
        if (check.length && check[0].uid && setDeleteThumbnails) {
            setDeleteThumbnails([
                ...deleteThumbnails,
                check[0].uid
            ])
        }
        setFileList(newFileList)
        setFile(newFileList)
    }

    useEffect(() => {
        if (listFile) {
            setFileList(listFile)
        }
    }, [listFile?.length])

    const uploadButton = (
        <div>
            <PlusOutlined />
            <div style={{ marginTop: 8 }}>Upload</div>
        </div>
    );

    return (
        <Upload
            action="https://run.mocky.io/v3/5377066f-b1b7-438d-8bcc-b58e5e0fb76f"
            listType="picture-card"
            fileList={fileList}
            onChange={handleChange}
        >
            {fileList.length >= 8 ? null : uploadButton}
        </Upload>
    )
}

export default UploadImage