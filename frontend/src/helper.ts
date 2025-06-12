import React from "react";

export function handleKeyDownNumber (e: React.KeyboardEvent<HTMLInputElement>) {
    const allowedKeys = [
        'Backspace',
        'Tab',
        'ArrowLeft',
        'ArrowRight',
        'Delete',
        'Home',
        'End'
    ];

    const isCtrlA = e.key === 'a' && (e.ctrlKey || e.metaKey);
    const isCtrlC = e.key === 'c' && (e.ctrlKey || e.metaKey);
    const isCtrlV = e.key === 'v' && (e.ctrlKey || e.metaKey);
    const isCtrlX = e.key === 'x' && (e.ctrlKey || e.metaKey);

    if (
        allowedKeys.includes(e.key) ||
        isCtrlA || isCtrlC || isCtrlV || isCtrlX
    ) {
        return;
    }

    if (/^\d$/.test(e.key)) {
        return;
    }

    if (e.key === ',') {
        if (e.currentTarget.value.includes(',')) {
            e.preventDefault();
        }
        return;
    }

    e.preventDefault();
}