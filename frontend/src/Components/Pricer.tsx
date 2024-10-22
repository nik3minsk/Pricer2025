import React, { useState } from 'react';
import { Container, Card, Button, Group, Text, Grid } from '@mantine/core';
import '@mantine/core/styles.css';

const PriceCard= (): React.JSX.Element =>  {
    return (
        <Card shadow="sm" padding="lg" style={{ marginBottom: '20px' }}>
            <Group position="apart" style={{ marginBottom: 5, marginTop: 'sm' }}>
                <Text weight={500}>{name}</Text>
                <Group>
                    <Button variant="light" color="blue" onClick={onEdit}>Редактировать</Button>
                    <Button variant="light" color="red" onClick={onDelete}>Удалить</Button>
                </Group>
            </Group>
        </Card>
    );
}

function PriceList() {
    const [prices, setPrices] = useState([
        { id: 1, name: 'Прайс 1' },
        { id: 2, name: 'Прайс 2' },
    ]);

    const addPrice = () => {
        const newPrice = { id: prices.length + 1, name: `Прайс ${prices.length + 1}` };
        setPrices([...prices, newPrice]);
    };

    const editPrice = (id) => {
        console.log(`Редактировать прайс с id: ${id}`);
        // Добавьте сюда вашу логику редактирования прайса
    };

    const deletePrice = (id) => {
        setPrices(prices.filter(price => price.id !== id));
    };

    return (
        <Container>
            <Button onClick={addPrice} style={{ marginBottom: '20px' }}>Добавить прайс</Button>
            <Grid>
                {prices.map(price => (
                    <Grid.Col span={12} key={price.id}>
                        <PriceCard
                            name={price.name}
                            onEdit={() => editPrice(price.id)}
                            onDelete={() => deletePrice(price.id)}
                        />
                    </Grid.Col>
                ))}
            </Grid>
        </Container>
    );
}

export default PriceCard;
