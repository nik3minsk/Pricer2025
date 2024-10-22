import '@mantine/core/styles.css';
import { TextInput, NumberInput, Button } from '@mantine/core';
import { useState } from 'react';
import axios from 'axios';

const BACK_URL: string = import.meta.env.VITE_BACK_URL;

function AddPrice() {
    const [value, setValue] = useState('');
    const [value1, setValue1] = useState<string | number>('');
    const [dateResponse, setDateResponse] = useState('');

    const handleSubmit = async (event: any) => {
        event.preventDefault();

        const response = await axios.post(`${BACK_URL}/api/currency`, {}, {
            headers: {
                'Content-Type': 'application/json',
            },
            params: {
                value: value,
                value1: value1
            },
        });

        const data = await response.data;
        console.log(data);
    };

    const handleDateSubmit = async () => {
        const currentDate = new Date().toISOString();

        const response = await axios.post(`${BACK_URL}/api/nbcurrency`, { date: currentDate }, {
            headers: {
                'Content-Type': 'application/json',
            },
        });

        const data = await response.data;
        setDateResponse(data);
    };

    return (
        <div>
            <b>Это работает???</b>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start', gap: '1em' }}>
                <table>
                    <tbody>
                    <tr>
                        <td><label>Название продукта</label></td>
                        <td>
                            <TextInput
                                value={value}
                                onChange={(event) => setValue(event.currentTarget.value)}
                                placeholder="Введите название"
                                required
                            />
                        </td>
                    </tr>
                    <tr>
                        <td><label>Цена</label></td>
                        <td>
                            <NumberInput
                                value={value1}
                                onChange={setValue1}
                                placeholder="Введите цену"
                                required
                            />
                        </td>
                    </tr>
                    <tr>
                        <td colSpan={2}>
                            <Button type="submit" style={{ marginRight: '10px' }}>Добавить</Button>
                            <Button onClick={handleDateSubmit}>Запросить курс НБ РБ</Button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
            <div>{dateResponse}</div>
        </div>
    );
}

export default AddPrice;
