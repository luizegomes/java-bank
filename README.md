# 🏦 DIO Bank – Sistema Bancário em Java

## 📌 Sobre o projeto

Este projeto é uma aplicação Java orientada a objetos que simula um sistema bancário básico. Desenvolvido como parte de um estudo prático para consolidar os principais conceitos da **Programação Orientada a Objetos (POO)**, o sistema permite:

- Criação de contas com múltiplas chaves PIX
- Depósitos e saques
- Transferências entre contas via PIX
- Criação e gerenciamento de investimentos
- Histórico de transações financeiras

A aplicação foi construída com foco em **herança**, **encapsulamento**, **polimorfismo**, **abstração** e **reuso de código**, utilizando estruturas limpas e extensíveis.

---

## 🧠 Conceitos aplicados

- **Herança:** Estrutura de carteiras (`Wallet`) especializadas em conta e investimento
- **Encapsulamento:** Proteção de dados sensíveis como saldo e histórico
- **Polimorfismo:** Operações financeiras adaptadas para diferentes tipos de carteira
- **Abstração:** Classes base para serviços bancários
- **Reuso de código:** Métodos genéricos para movimentações e auditoria

---

## 🚀 Como executar o projeto

### ✅ Pré-requisitos

- [Java JDK 17+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [NetBeans IDE](https://netbeans.apache.org/download/index.html)

### 📦 Passos para rodar

1. Clone o repositório:
   ```bash
   git clone https://github.com/luizegomes/java-bank.git

2. **Abra o projeto no NetBeans:**
   - Vá em `File > Open Project`
   - Selecione a pasta do projeto

3. **Execute o projeto:**
   - Clique com o botão direito no projeto
   - Selecione `Run`

4. **Interaja pelo terminal:**
   - O sistema exibirá um menu com opções como criar conta, investir, sacar, transferir, etc.

---

## 📚 Funcionalidades principais

- [x] Criar conta com múltiplas chaves PIX  
- [x] Depositar e sacar valores  
- [x] Transferir entre contas via PIX  
- [x] Criar investimentos com taxa de rendimento  
- [x] Investir e resgatar valores  
- [x] Atualizar investimentos com rendimento  
- [x] Listar contas, investimentos e carteiras  
- [x] Consultar histórico de transações  

---

## 🛠️ Tecnologias utilizadas

- Java 17  
- NetBeans IDE  
- Paradigma Orientado a Objetos  
- Lombok (para simplificação de getters/setters e `toString`)  
- Java Collections API  
