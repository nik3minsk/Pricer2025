// Понял, давайте сделаем кнопку видимой, но недоступной для нажатия, если уже существует прайс с isGeneralPrice равным true. Для этого можно использовать свойство disabled и добавить стили, чтобы кнопка выглядела как обычно, но была неактивной.
//
//     Вот обновленный код:

    import React, { useState, useEffect } from "react";
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';
import '@mantine/dropzone/styles.css';
import { Button, Group, Stack, Checkbox } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import SellerAddAndEdit from './SellerAddAndEdit.tsx';
import axios from "axios";

const BACK_URL = import.meta.env.VITE_BACK_URL;

const HomePage = (): React.JSX.Element => {
    const [openedGeneralPrice, { open: openGeneralPrice, close: closeGeneralPrice }] = useDisclosure(false);
    const [openedSupplierPrice, { open: openSupplierPrice, close: closeSupplierPrice }] = useDisclosure(false);
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

    return (
        <Stack align="center" justify="center" style={{ minHeight: '100vh' }}>
            <Group align="start" style={{ width: '100%', border: '2px solid #ccc', padding: '20px', borderRadius: '10px' }}>
                <div style={{ flex: 1 }}>
                    <h2>Список продавцов</h2>
                    <div style={{ maxHeight: '600px', overflowY: 'auto', paddingRight: '10px', minHeight: '300px' }}>
                        {sellers.slice(0, 10).map(seller => (
                            <Checkbox
                                key={seller.id}
                                label={`${seller.priceName} - ${seller.pathToPrice}`}
                                checked={selectedSellerId === seller.id}
                                onChange={() => handleCheckboxChange(seller.id)}
                                style={{
                                    marginBottom: '10px',
                                    color: seller.isGeneralPrice ? 'green' : 'inherit'
                                }}
                            />
                        ))}
                    </div>
                </div>
                <div style={{
                    flex: 1,
                    justifyContent: 'space-between',
                    height: '100%'
                }}>
                    <Button
                        onClick={openGeneralPrice}
                        fullWidth
                        style={{ marginBottom: '10px', cursor: hasGeneralPrice ? 'not-allowed' : 'pointer' }}
                        disabled={hasGeneralPrice}
                    >
                        Добавить собственный прайс
                    </Button>
                    <Button onClick={openSupplierPrice} fullWidth style={{ marginBottom: '10px' }}>
                        Добавить поставщика
                    </Button>
                    <Button onClick={handleDelete} fullWidth>Удалить прайс</Button>
                    {serverResponse && (
                        <pre style={{ marginTop: '20px', color: 'green' }}>{serverResponse}</pre>
                    )}
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
        </Stack>
    );
}

export default HomePage;

// Теперь кнопка "Добавить собственный прайс" будет видимой, но неактивной (нельзя нажать), если уже существует прайс с isGeneralPrice равным true. Если у вас есть еще вопросы или нужна дополнительная помощь, дайте знать! blush
