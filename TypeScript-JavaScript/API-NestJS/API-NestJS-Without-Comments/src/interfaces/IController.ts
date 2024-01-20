export interface returnHandle {
    data: any;
}

export interface IController {
    handle(...args: any[]): Promise<returnHandle>;
}
