import React, { useEffect, useState } from "react";
import { Select } from "@mantine/core";
import axios from "axios";

const BACK_URL: string = import.meta.env.VITE_BACK_URL;

const FileSelector = ({ folderPath, onSelectFile }) => {
    const [files, setFiles] = useState<string[]>([]);
    const [selectedFile, setSelectedFile] = useState<string | null>(null);

    useEffect(() => {
        const fetchFiles = async () => {
            try {
                const response = await axios.post(`${BACK_URL}/api/files`, { folderPath });
                setFiles(response.data);
            } catch (error) {
                console.error("Ошибка при получении файлов:", error);
            }
        };
        if (folderPath) {
            fetchFiles();
        }
    }, [folderPath]);

    const handleFileChange = (file) => {
        setSelectedFile(file);
        if (onSelectFile) {
            onSelectFile(file);
        }
    };

    return (
        <Select
            label="Выберите файл для настройки"
            placeholder="Выберите файл"
            value={selectedFile}
            onChange={handleFileChange}
            data={files.map(file => ({ value: file, label: file }))}
        />
    );
};

export default FileSelector;
