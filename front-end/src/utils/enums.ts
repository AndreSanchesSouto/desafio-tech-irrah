export const Role = {
    ADMINER: "adminer",
    COMMON: "common"
} as const;

export function setRole(status: string | null): string {
    switch (status?.toLocaleLowerCase()) {
        case Role.ADMINER: return "Administrador";
        case Role.COMMON: return "Usuário";
        default: return "Desconecido";
    }
}