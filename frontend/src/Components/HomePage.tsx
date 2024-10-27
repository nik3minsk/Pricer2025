
import React, { useState, useEffect } from "react";
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';
import '@mantine/dropzone/styles.css';
import { Button, Group, Stack, Checkbox } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import PriceAddAndEdit from './priceAddAndEdit';
import axios from "axios";

const BACK_URL = import.meta.env.VITE_BACK_URL;

const HomePage = (): React.JSX.Element => {
    const [openedGeneralPrice, { open: openGeneralPrice, close: closeGeneralPrice }] = useDisclosure(false);
    const [openedSupplierPrice, { open: openSupplierPrice, close: closeSupplierPrice }] = useDisclosure(false);
    const [serverResponse, setServerResponse] = useState('');
    const [responseType, setResponseType] = useState('');
    const [sellers, setSellers] = useState<SellerDTO[]>([]);
    const [selectedSellerId, setSelectedSellerId] = useState<number | null>(null);

    interface SellerDTO {
        id: number;
        priceName: string;
        pathToPrice: string;
        isGeneralPrice: boolean;
    }

    useEffect(() => {
        const fetchSellers = async () => {
            try {
                const response = await axios.get(`${BACK_URL}/api/sellers`);
                setSellers(response.data);
                console.log(response);
            } catch (error) {
                console.error('Ошибка при получении данных о продавцах:', error);
            }
        };
        fetchSellers();
    }, []);

    useEffect(() => {
        if (serverResponse) {
            const timer = setTimeout(() => {
                setServerResponse('');
                setResponseType('');
            }, 2000);
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

    return (
        <Stack align="center" justify="center" style={{ minHeight: '100vh' }}>
            <Group align="start" style={{ width: '100%', border: '2px solid #ccc', padding: '20px', borderRadius: '10px' }}>
                <div style={{ flex: 1 }}>
                    <h2>Прайс-листы</h2>
                    <div style={{ maxHeight: '600px', overflowY: 'auto', paddingRight: '10px', minHeight: '300' }}>
                        {sellers.slice(0, 10).map(seller => (
                            <Checkbox
                                key={seller.id}
                                label={`${seller.priceName} - ${seller.pathToPrice}`}
                                checked={selectedSellerId === seller.id}
                                onChange={() => handleCheckboxChange(seller.id)}
                                style={{ marginBottom: '10px' }}
                            />
                        ))}
                    </div>
                </div>
                <div style={{ flexShrink: 0, display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
                    <Button onClick={openGeneralPrice} fullWidth style={{ marginBottom: '10px' }}>Добавить собственный прайс</Button>
                    <Button onClick={openSupplierPrice} fullWidth style={{ marginBottom: '10px' }}>Добавить прайс поставщика</Button>
                    <Button onClick={handleDelete} fullWidth>Удалить прайс</Button>
                    {serverResponse && (
                        <pre style={{ marginTop: '20px', color: 'green' }}>{serverResponse}</pre>
                    )}
                </div>
            </Group>
            <PriceAddAndEdit
                isGeneralPrice={true}
                priceNumber={0}
                opened={openedGeneralPrice}
                close={closeGeneralPrice}
                setServerResponse={setServerResponse}
                setResponseType={setResponseType}
            />
            <PriceAddAndEdit
                isGeneralPrice={false}
                priceNumber={0}
                opened={openedSupplierPrice}
                close={closeSupplierPrice}
                setServerResponse={setServerResponse}
                setResponseType={setResponseType}
            />
        </Stack>
    );
}

export default HomePage;
