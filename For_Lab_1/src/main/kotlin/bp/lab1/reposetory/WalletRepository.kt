package bp.lab1.reposetory

import bp.lab1.models.Wallet
import org.springframework.data.jpa.repository.JpaRepository

interface WalletRepository:JpaRepository<Wallet,Long> {
}