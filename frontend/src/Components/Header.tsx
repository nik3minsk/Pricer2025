import React from "react";
import {AppShell, Burger, Center, Container, Group, Menu, Button} from "@mantine/core";
import {useDisclosure} from "@mantine/hooks";
import {useLocation} from "wouter";

const links = [
    // {link: '/about', label: 'Features'},
    {
        link: '#1',
        label: 'Price manage',
        links: [
            // { link: '/docs', label: 'Documentation' },
            {link: '/addprice', label: 'Add a new price'},
            {link: '/resources', label: 'Resources'},
            {link: '/community', label: 'Community'},
            {link: '/blog', label: 'Blog'},
        ],
    },
    {link: '/about', label: 'About'},
    // {link: '/pricing', label: 'Pricing'},
    {link: '/pricer', label: 'Price load'},
    // {link: '/AuthorisationPage', label: 'AuthorisationPage'},
    // {
    //     link: '#2',
    //     label: 'Support',
    //     links: [
    //         {link: '/faq', label: 'FAQ'},
    //         {link: '/demo', label: 'Book a demo'},
    //         {link: '/forums', label: 'Forums'},
    //     ],
    // },
];


const Header = (): React.JSX.Element => {
    const [opened, {toggle}] = useDisclosure(false);
    const [location, navigate] = useLocation()
    const items = links.map((link) => {
        const menuItems = link.links?.map((item) => (
            <Menu.Item key={item.link}>
                <Button onClick={() => navigate(item.link)}>
                    {item.label}
                </Button>
            </Menu.Item>
        ));

        if (menuItems) {
            return (
                <Menu key={link.label} trigger="hover" transitionProps={{exitDuration: 0}} withinPortal>
                    <Menu.Target>
                        <Button onClick={() => navigate(link.link)}>
                            {link.label}
                        </Button>
                    </Menu.Target>
                    <Menu.Dropdown>{menuItems}</Menu.Dropdown>
                </Menu>
            );
        }

        return (
            <Button
                key={link.label}
                onClick={() => navigate(link.link)}
            >
                {link.label}
            </Button>
        );
    });
    return (
        <AppShell.Header className={".header"}>
            <Container size="md">
                <div className={".inner"}>
                    <Group gap={5} visibleFrom="sm">
                        {items}
                    </Group>
                    <Burger opened={opened} onClick={toggle} size="sm" hiddenFrom="sm"/>
                </div>
            </Container>
        </AppShell.Header>
    )
}
export default Header