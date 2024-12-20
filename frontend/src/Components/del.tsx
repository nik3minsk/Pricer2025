import React, { useState, useEffect } from "react";
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';
import '@mantine/dropzone/styles.css';
import { Button, Group, Stack, Checkbox, Modal, Box } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import SellerAddAndEdit from './SellerAddAndEdit.tsx';
import axios from "axios";
import './index.css'; // Импортируем наш CSS файл

const BACK_URL = import.meta.env.VITE_BACK_URL;

const HomePage = (): React.JSX.Element => {
    const [openedGeneralPrice, { open: openGeneralPrice, close: closeGeneralPrice }] = useDisclosure(false);
    const [openedSupplierPrice, { open: openSupplierPrice, close: closeSupplierPrice }] = useDisclosure(false);
    const [openedHelp, { open: openHelp, close: closeHelp }] = useDisclosure(false);
    const [serverResponse, setServerResponse] = useState('');
    const [responseType, setResponseType] = useState('');
    const [sellers, setSellers] = useState<SellerDTO[]>([]);
    const [selectedSellerId, setSelectedSellerId] = useState<number | null>(null);
    const [hasGeneralPrice, setHasGeneralPrice] = useState(false);

    interface SellerDTO {
        id: number;
        priceName: string;
        pathToPrice: string;
        isGeneralPrice: boolean;
    }

    const fetchSellers = async () => {
        try {
            const response = await axios.get(`${BACK_URL}/api/sellers`);
            setSellers(response.data);
            setHasGeneralPrice(response.data.some((seller: SellerDTO) => seller.isGeneralPrice));
            console.log(response);
        } catch (error) {
            console.error('Ошибка при получении данных о продавцах:', error);
        }
    };

    useEffect(() => {
        fetchSellers();
    }, []);

    useEffect(() => {
        if (serverResponse) {
            const timer = setTimeout(() => {
                setServerResponse('');
                setResponseType('');
                fetchSellers();
            }, 700);
            return () => clearTimeout(timer);
        }
    }, [serverResponse]);

    const handleCheckboxChange = (id: number) => {
        setSelectedSellerId(selectedSellerId === id ? null : id);
    };

    const handleDelete = async () => {
        if (selectedSellerId) {
            try {
                await axios.delete(`${BACK_URL}/api/sellers/${selectedSellerId}`);
                setSellers(sellers.filter(seller => seller.id !== selectedSellerId));
                setSelectedSellerId(null);
                setServerResponse('Прайс успешно удален');
            } catch (error) {
                console.error('Ошибка при удалении прайса:', error);
                setServerResponse('Ошибка при удалении прайса');
            }
        } else {
            setServerResponse('Выберите прайс для удаления');
        }
    };

    const handleComparePrices = async () => {
        const sellerData = sellers.map(seller => ({
            priceName: seller.priceName,
            isGeneralPrice: seller.isGeneralPrice
        }));

        try {
            const response = await axios.post(`${BACK_URL}/api/files/compare`, sellerData);
            setServerResponse(`Файл сравнения создан: ${response.data}`);
        } catch (error) {
            console.error('Ошибка при сравнении цен:', error);
            setServerResponse('Ошибка при сравнении цен');
        }
    };

    return (
        <Stack align="center" justify="center" className="main-container">
            <Group align="start" className="group-container">
                <div className="sellers-list">
                    <h2>Список продавцов</h2>
                    <div className="sellers-checkboxes">
                        {sellers.slice(0, 10).map(seller => (
                            <Checkbox
                                key={seller.id}
                                label={
                                    seller.isGeneralPrice
                                        ? `Наш прайс: ${seller.priceName} - ${seller.pathToPrice}`
                                        : `Поставщик: ${seller.priceName} - ${seller.pathToPrice}`
                                }
                                checked={selectedSellerId === seller.id}
                                onChange={() => handleCheckboxChange(seller.id)}
                                className={seller.isGeneralPrice ? 'general-price' : ''}
                            />
                        ))}
                    </div>
                    <Box className="server-response">
                        {serverResponse && (
                            <pre>{serverResponse}</pre>
                        )}
                    </Box>
                </div>
                <div className="buttons-container">
                    <Button
                        onClick={openGeneralPrice}
                        fullWidth
                        className="action-button"
                        disabled={hasGeneralPrice}
                    >
                        Добавить собственный прайс
                    </Button>
                    <Button
                        onClick={openSupplierPrice}
                        fullWidth
                        className="action-button"
                        disabled={sellers.length >= 4}
                    >
                        Добавить поставщика
                    </Button>
                    <Button onClick={handleDelete} fullWidth className="action-button">Удалить прайс</Button>
                    <Button
                        onClick={handleComparePrices}
                        fullWidth
                        className="action-button compare-button"
                    >
                        Сравнить цены
                    </Button>
                    <Button
                        onClick={openHelp}
                        fullWidth
                        className="action-button help-button"
                    >
                        Help
                    </Button>
                </div>
            </Group>
            <SellerAddAndEdit
                isGeneralPrice={true}
                priceNumber={0}
                opened={openedGeneralPrice}
                close={closeGeneralPrice}
                setServerResponse={setServerResponse}
                setResponseType={setResponseType}
            />
            <SellerAddAndEdit
                isGeneralPrice={false}
                priceNumber={0}
                opened={openedSupplierPrice}
                close={closeSupplierPrice}
                setServerResponse={setServerResponse}
                setResponseType={setResponseType}
            />
            <Modal opened={openedHelp} onClose={closeHelp} title="Help" centered size="lg" className="modalCentered">
                <p>
                    Эта программа позволяет управлять списком продавцов, добавлять и удалять прайсы, а также сравнивать
                    цены между различными поставщиками. Используйте кнопки для выполнения соответствующих действий.
                </p>
            </Modal>
        </Stack>
    );
}

export default HomePage;
